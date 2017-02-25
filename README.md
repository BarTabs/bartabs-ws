## BarTabs Web Service

**Description:** This project contains the backend web services for BarTabs. These web services provide a flexible API, which can be used
by any front end GUI. It leverages the Spring MVC framework and uses Maven for dependency management.

**How to get started:**

- On MAC:
  1. Install Homebrew:
    - Launch a terminal window
    - Run `/usr/bin/ruby -e "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)"`
  2. Install Maven: `brew install maven`
  3. Download & Install [Eclipse](http://www.eclipse.org/downloads/)
    - Install the [Maven plugin](http://stackoverflow.com/questions/8620127/maven-in-eclipse-step-by-step-installation) for Eclipse
  4. `git clone` this project as well as the [bartabs-server](https://github.com/BarTabs/bartabs-server)
  5. Import bartabs-ws and bartabs-server into your Eclipse as Maven projects
  
  
**How to run on local server:**

1. Install Tomcat8 and [configure](http://crunchify.com/step-by-step-guide-to-setup-and-install-apache-tomcat-server-in-eclipse-development-environment-ide/)
it on Eclipse.
2. Under the Servers tab, right-click the tomcat server and select `add/remove projects`, then add the **bartabs-server** project
3. Add your own **server.xml**, **auth0.properties**, and **database.properties** files to map the right configs
  - *BarTab developers, these files can be found on the server hosting tomcat in*  `/opt/tomcat/conf/`
4. Start the server

**How to deploy:**
1. Run `mvn install` in the root directory of **bartabs-ws**
2. Run `mvn package` in the root directory of **bartabs-server**
  - This will create a deployable **.war** file that can be found under the `/bartabs-server/target` directory
3. Deploy this **.war** file from your tomcat server by adding it to the tomcat `/webapps` folder and restarting the service
  - Note: The tomcat server must have the **server.xml**, **auth0.properties**, and **database.properties** in its `/conf` directory
