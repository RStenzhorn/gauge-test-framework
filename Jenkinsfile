pipeline {
    options {
        buildDiscarder(logRotator(numToKeepStr: '5', artifactNumToKeepStr: '5'))
        timeout(time: 2)
    }
    parameters {
        string(name: 'SPEC', defaultValue: '', description: '')
    }
    agent {
        kubernetes {
            yaml '''
        apiVersion: v1
        kind: Pod
        spec:
          containers:
          - name: gauge
            image: toxiccuss/gauge:0.1.0-jdk11
            command: ["cat"]
            tty: true
          - name: maven
            image: maven:3.8.5-openjdk-17
            command: ["cat"]
            tty: true
            volumeMounts:
              - name: maven-secret
                mountPath: /usr/maven
          volumes:
            - name: maven-secret
              secret:
                secretName: maven-settings-secret
                items:
                  - key: settings.xml
                    path: settings.xml
        '''
        }
    }
    stages {
        stage('Maven: BUILD') {
            steps {
                container('maven') {
                    sh 'mvn package -s /usr/maven/settings.xml -Dmaven.wagon.http.ssl.insecure=true'
                }
            }
        }
        stage('Gauge: TEST') {
            steps {
                container('gauge') {
                    script {
                        sh """ gauge config runner_connection_timeout 200000"""
                        sh """ gauge config runner_request_timeout 500000"""
                        try {
                            if (params.SPEC.isEmpty()) {
                                sh """gauge -v run specs """
                            }
                        } catch (Exception ignored) {
                            currentBuild.result = 'UNSTABLE'
                        }
                    }
                }
            }
        }
        stage('HTML: PUBLISH') {
            steps {
                publishHTML(target: [allowMissing         : false,
                                     alwaysLinkToLastBuild: true,
                                     keepAll              : true,
                                     reportDir            : 'reports/html-report',
                                     reportFiles          : 'index.html',
                                     reportName           : 'HTML Repots',
                                     reportTitles         : 'Gauge REPORT'])
            }
        }
    }
}