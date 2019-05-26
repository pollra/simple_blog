node('master'){
    slackSend message: "Build start[simple-blog] : <${env.BUILD_URL} | ${env.JOB_NAME}>"
    catchError {
        def result = 0
        sh 'git clone https://github.com/pollra/simple_blog.git'
        echo 'git clone complete'

        result = result + 1
        sh 'gradle clean build -x test'
        echo 'gradle build complete'

        result = result + 1
        sh 'java -jar simple_blog.jar'
        echo 'jar running'
    }
    slackSend message: "배포 상태::${result}: <${env.BUILD_URL} | ${env.JOB_NAME}>"
}