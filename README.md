# project

# project 
# Mailer Application

Overview

Mailer application is a simple application mimicking the Email.
This Application has the following features:
 1) Allowing the new users to register themselves.
 
 2) Allowing the already registered users to login with their credentials
 
 3) Allowing the authenticated users to view his inbox, sent mails, deleted mails and saved   	drafts.
 
 4) Allowing the authenticated users to compose and send a mail or save a mail draft.
 
 5) Users can edit or delete their saved drafts.
   
 6) Allowing the authenticated users to restore their mails from trash box or permanently  	delete the mails in trash box.
 
 7) Forgot password feature for users who forgot their password 
   
   
  Steps to build and install.
  
  Dependencies
  	1) Maven
  	2) Tomcat
  
  Installing Maven
  	1) cd /opt/   [Moving to /opt folder for installing 3rd party libraries]
  	
  	2) wget  http://www-eu.apache.org/dist/maven/maven-3/3.3.9/binaries/apache-maven-3.3.9-bin.tar.gz [download the compressed file with latest version]
  	
  	3) sudo tar -xvzf apache-maven-3.3.9-bin.tar.gz  [extract the compressed file]
  	
  	4) sudo mv apache-maven-3.3.9 maven  [rename the folder name to maven]
  	
  	5) sudo gedit /etc/profile.d/mavenenv.sh [setting up the environment variable]
  	
  	6) export M2_HOME=/opt/maven
       export PATH=${M2_HOME}/bin:${PATH}  [add the following lines to mavenenv.sh]
       
 	7) sudo chmod +x /etc/profile.d/mavenenv.sh 
	   sudo source /etc/profile.d/mavenenv.sh  [first make the script as executable and run it 	   in current shell environment]
	   
	8) mvn --version [checking the maven version after successful installation]
  	 
   Installing Tomcat
     1) sudo apt-get install tomcat8 tomcat8-docs tomcat8-examples tomcat8-admin
     2) systemctl start/restart/status/stop tomcat8 [for starting, restartin, checking status   	    and stopping tomcat respectively ]
      
  	
  	
  After installing the required dependencies, the steps to be followed are: 	
  	
   1) Go to your project folder where pom.xml file is present.
   
   2) Issue command " mvn clean install " to clean the previous builds and generate a new 	  	  	  build.
   
   3) The newly generated build(war/jar) is present in /project_folder/target/ folder.
   
   4) If you want to run the artifact locally then issue the command "java -jar 	 	 	 	 	  artifact-name.extension" in "target" folder.
   
   5) If you want to deploy the war file to a tomcat container, then copy the war to webapps  	 	  folder in tomcat(/tomcat/webapps/) and start the tomcat container.   