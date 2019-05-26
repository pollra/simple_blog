def err = null
try{
    node('master'){
        slackSend message: "Build start[simple-blog] : <${env.BUILD_URL} | ${env.JOB_NAME}>"

        sh 'git clone https://github.com/pollra/simple_blog.git'
        echo 'git clone complete'

        sh 'gradle clean build -x test'
        echo 'gradle build complete'

        sh 'java -jar simple_blog.jar'
        echo 'jar running'
    }
}
catch (err) {
    currentBuild.result = "FAILURE"
}
finally{
    (currentBuild.result != "ABORTED"){
        slackSend message: "jar 배포 실패[simple-blog] : <${env.BUILD_URL} | ${env.JOB_NAME}>"
    }
}