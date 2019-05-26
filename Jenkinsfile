
node('master'){
    slackSend message: "Build start[simple-blog] : <${env.BUILD_URL} | ${env.JOB_NAME}>"

    try{
        sh 'sudo git clone https://github.com/pollra/simple_blog.git'
        echo 'git clone complete'
    }catch(err){
        currentBuild.result = "FAILURE"

    }finally{
        (currentBuild.result != "ABORTED"){
            slackSend message: "git 에서 클론받지 못했습니다.[simple-blog] : <${env.BUILD_URL} | ${env.JOB_NAME}>"
        }
    }

    try{
        sh 'sudo gradle clean build -x test'
        echo 'gradle build complete'
    }catch(err){
        currentBuild.result = "FAILURE"
    }finally{
        (currentBuild.result != "ABORTED"){
            slackSend message: "gradle 빌드 실패[simple-blog] : <${env.BUILD_URL} | ${env.JOB_NAME}>"
        }
    }

    try{
        sh 'java -jar simple_blog.jar'
        echo 'jar running'
    }catch(err){
        currentBuild.result = "FAILURE"
    }finally{
        (currentBuild.result != "ABORTED"){
            slackSend message: "jar 배포 실패[simple-blog] : <${env.BUILD_URL} | ${env.JOB_NAME}>"
        }
    }
}