pipeline {
    options {
        buildDiscarder(logRotator(numToKeepStr: '5', artifactNumToKeepStr: '5'))
        timeout(time: 2)
    }
    parameters {
        string(name: 'SPEC', defaultValue: 'specs/example.spec', description: 'Path to Specfile')
    }
    agent {
        kubernetes {
            yaml '''
        apiVersion: v1
        kind: Pod
        spec:
          containers:
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
                container('maven') {
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