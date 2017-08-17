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
        }
    }, integration: {
        node {
            checkout scm
            unstash 'build'
            sh './gradlew integration'
            junit 'build/test-results/integration/*.xml'
            stash includes: 'build/jacoco/integration.exec', name: 'jacocoIntegration'
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
    }, cpd: {
        node {
            checkout scm
            unstash 'build'
            sh './gradlew cpd'
            step([$class: 'DryPublisher', canRunOnFailed: true, pattern: 'build/reports/cpd/*.xml'])
        }
    }, pmd: {
        node {
            checkout scm
            unstash 'build'
            sh './gradlew pmdMain pmdTest pmdFunctionalTest'
            step([$class: 'PmdPublisher', canRunOnFailed: true, pattern: 'build/reports/pmd/*.xml'])
        }
    }, findbugs: {
        node {
            checkout scm
            unstash 'build'
            sh './gradlew findbugsMain findbugsTest findbugsFunctionalTest'
            step([$class: 'FindBugsPublisher', canRunOnFailed: true, pattern: 'build/reports/findbugs/*.xml'])
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
