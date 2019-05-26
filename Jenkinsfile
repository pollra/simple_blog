node('master'){
    slackSend message: "Build start[simple-blog] : <${env.BUILD_URL} | ${env.JOB_NAME}>"
    catchError {
        def result = 0
        stage('Source'){
            git 'https://github.com/pollra/simple_blog.git'
            result = result + 1
        }
        stage('Compile'){
            sh "gradle clean build -x test"
            result = result + 1
        }
    }
    slackSend message: "배포 상태::${result}: <${env.BUILD_URL} | ${env.JOB_NAME}>"
}