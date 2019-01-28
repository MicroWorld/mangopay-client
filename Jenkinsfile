#!groovy

stage('Build') {
    node {
        checkout scm
        sh './gradlew clean assemble'
        stash includes: 'build/**/*', name: 'build'
    }
}
stage('Tests') {
    parallel(unit: {
        node {
            checkout scm
            unstash 'build'
            sh './gradlew test'
            junit 'build/test-results/test/*.xml'
            stash includes: 'build/jacoco/test.exec', name: 'jacocoTest'
            stash includes: 'build/test-results/test/*.xml', name: 'junitTest'
        }
    }, integration: {
        node {
            checkout scm
            unstash 'build'
            sh './gradlew integration'
            junit 'build/test-results/integration/*.xml'
            stash includes: 'build/jacoco/integration.exec', name: 'jacocoIntegration'
            stash includes: 'build/test-results/integration/*.xml', name: 'junitIntegration'
        }
    })
}
stage('Checks') {
    parallel(warnings: {
        node {
            checkout scm
            unstash 'build'
            step([$class: 'WarningsPublisher', canRunOnFailed: true, consoleParsers: [[parserName: 'Java Compiler (javac)']]])
        }
    }, jacoco: {
        node {
            checkout scm
            unstash 'build'
            unstash 'jacocoTest'
            unstash 'jacocoIntegration'
            step([$class: 'JacocoPublisher', execPattern: 'build/jacoco/*.exec', classPattern: 'build/classes/java/main'])
        }
    })
}
if (scm.branches[0].name == 'master') {
    stage('SonarQube Analysis') {
        node {
            checkout scm
            dir('microworldplus') {
                withSonarQubeEnv('Sonar') {
                    unstash 'build'
                    unstash 'jacocoTest'
                    unstash 'jacocoIntegration'
                    unstash 'junitTest'
                    unstash 'junitIntegration'
                    sh './gradlew --info sonarqube'
                }
            }
        }
    }
    stage('SonarQube Quality Gate'){
        timeout(time: 1, unit: 'HOURS') {
            node {
                def qg = waitForQualityGate()
                if (qg.status != 'OK') {
                  error "Pipeline aborted due to quality gate failure: ${qg.status}"
                }
            }
        }
    }
}
stage('Archive') {
    node {
        checkout scm
        unstash 'build'
        sh './gradlew uploadArchives'
    }
}
stage('Build for production') {
    milestone 10
    timeout(time:7, unit:'DAYS') {
        input message:'Make a production build (version change)?'
    }
    milestone 11
    node {
        checkout scm
        unstash 'build'
        sh './gradlew release'
    }
}
