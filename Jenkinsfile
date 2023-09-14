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
        '''
        }
    }
    stages {
        stage('Gauge: TEST') {
            steps {
                container('gauge') {
                    script {
                        try {
                            if(params.SPEC.isEmpty()) {
                                sh """gauge run specs"""
                            } else {
                                sh """mvn gauge:execute -DspecsDir=specs'\''${params.SPEC}'.spec"""
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