slackSend message: "Build start[simple-blog] : <${env.BUILD_URL} | ${env.JOB_NAME}>"
sh 'sudo git clone https://github.com/pollra/simple_blog.git'
echo 'git clone complete'
sh 'sudo gradle clean build -x test'
echo 'gradle build complete'
sh 'java -jar simple_blog.jar'
echo 'jar running'