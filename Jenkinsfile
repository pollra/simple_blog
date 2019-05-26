slackSend message: "Build start[simple-blog] : <${env.BUILD_URL} | ${env.JOB_NAME}>"
sh '''set +xe
sudo git clone https://github.com/pollra/simple_blog.git
sudo gradle clean build -x test
java -jar simple_blog.jar