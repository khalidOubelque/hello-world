node{
      def mvnHome = tool name: 'M2_HOME', type: 'maven' 
      stage('Checkout'){
         git 'https://github.com/khalidOubelque/hello-world.git'
       
      }  
      stage('Build'){
         //// Get maven home path and build
        sh "${mvnHome}/bin/mvn clean package -Dmaven.test.skip=true"
      }
     stage ('Test-JUnit'){
         sh "'${mvnHome}/bin/mvn' test surefire-report:report"
      }  
    
      stage('Deploy') {     
            sshagent(['dockerHost']) {
               sh 'scp -o StrictHostKeyChecking=no webapp/target/*.war dockeradmin@ 172.31.35.165:/homme/dockeradmin/webapp/target/testJenkinsFile.war'
              
          }   
     }
      
 }
