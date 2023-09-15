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
            image: toxiccuss/gauge:0.0.6
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
        stage('Gauge: TEST') {
            steps {
                container('gauge') {
                    script {
                        try {
                            if (!params.SPEC.isEmpty()) {
                                sh """mvn gauge:execute -DspecsDir=${params.SPEC}"""
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