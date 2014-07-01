Jenkins Plug-in for Qubell Platform
===================================

[![Build Status](https://travis-ci.org/qubell/contrib-jenkins-qubell-plugin.png?branch=master)](https://travis-ci.org/qubell/contrib-jenkins-qubell-plugin)

Functionality
-------------

The Jenkins Plug-in for [Qubell Platform](http://qubell.com) allows you to launch and manipulate Qubell instances as part of a [Jenkins](http://jenkins-ci.org/) build job.
The plug-in enables you to perform the following tasks:

* Configure access credentials as part of a global configuration.
* Launch a new instance from a manifest located in the source repository.
* Execute a custom job on the newly launched instance.
* Destroy the newly launched instance.
* Execute a custom job on an instance specified by instance ID.
* Fail the build job if any of the preceding actions are incomplete after a configurable timeout.
* Gather instance properties and job execution results in a JSON file to be consumed by the rest of the pipeline.

Additionally, the following features are available: 

* Macros, which resolve in all steps and fields (in the form `${PARAM_NAME}`).
* Application and environment hints that are displayed as you type.
* (Experimental) Async execution to speed up parallel instance execution and command processing.

Compatibility
-------------

[Qubell Platform API v. 1.3+](http://docs.qubell.com/api/contents.html) or Qubell Platform R19+ (Express and Enterprise).

Getting Started
---------------

The following sections will guide you through the process of installing and configuring the Jenkins plug-in
for [Qubell Platform](http://qubell.com).

### Installation ###

1. If you have not already installed Jenkins, you can download the Java Web Archive (.war) or native package
for your specific operating system from the [Jenkins website](http://jenkins-ci.org/).
2. Download the current release of the Jenkins plug-in for Qubell Platform (.hpi) from [GitHub](https://github.com/qubell/contrib-jenkins-qubell-plugin/releases).
3. Install the Jenkins plug-in. You can either install it:
  * Via the Jenkins web UI by navigating to `Manage Jenkins > Manage Plugins > Advanced` and uploading the plug-in's .hpi file.
  * By placing the .hpi file into the `$JENKINS_HOME/plugins` directory and restarting Jenkins.

    To verify that the plug-in has been installed correctly, open the Jenkins web UI and navigate to `Manage Jenkins > Manage Plugins > Installed`.
You should see "Qubell Plugin" enabled in the list.

### Credentials ###

**NOTE:** These steps only need to be performed once. If your credentials are incorrect, you will not be able to access Qubell functionality.

Set up your credentials by following the steps below.

1. Within the Jenkins web UI, navigate to `Manage Jenkins > Configure System`.
2. Scroll to `Qubell Account Configuration`.
3. Input the `API URL` (URL to your Qubell instance), `Login` and `Password`. We suggest creating a deployment bot and using its credentials here.
4. Check the `Status Polling Interval` (5 seconds by default) and increase if appropriate. This interval tells Jenkins how often it should poll
Qubell for updates.
  
### Configuring a Job ###

To configure a new job within the Jenkins web UI, navigate to `New Item`, name your build, and select a build type. On the next screen, the following Qubell options are 
available under the `Add Build Step` drop-down list:
  
  * Qubell: Destroy Instance
  * Qubell: Launch Application Instance
  * Qubell: Run Command
  * Qubell: Wait for job completion
  
When one of these options is selected, the UI will expand to display configurable parameters.

#### Destroy Instance ####

  * By default, the `Destroy Instance` step expects an instance ID to be created during the `Start Instance` step; however, you can specify a `Custom Instance ID` 
and omit the `Start Instance` step.
  * Specify a `Timeout` in seconds to limit destroy time.
  * Select `Advanced...` to view the `When instance fails` drop-down list, which includes the following options:
    * Fail build
    * Mark build unstable
    * Ignore failure

#### Launch Application Instance ####

**NOTE:** In most cases, you will need one instance per job. This step will store the Launched Instance ID in context.

  * The `Launch Application Instance` step will launch an instance based on the inputted `Application ID` and (optional) `Environment ID`. Note that matching applications 
  are displayed as you type inside the `Application ID` field.
  * Specify a `Timeout` in seconds to limit launch time.
  * Tune your job by inputting `Extra Parameters` in json format(e.g. revision ID).
  * The `Manifest Relative Path` field is used when you need to upload your manifest to the app each time and it is stored under source control version.
  * The `Relative path to command output file` field identifies the location where the last command response will be stored (json file).
  * Select `Advanced...` to view the `When instance fails` drop-down list, which includes the following options:
    * Fail build
    * Mark build unstable
    * Ignore failure

#### Run Command ####

**NOTE:** `Execute asynchronously` is experimental. 

  * Execute `Name` command of context instance or a `Custom Instance ID`.
  * Specify a `Timeout` in seconds to limit launch time.
  * Tune your job by inputting `Extra Parameters` in json format(e.g. revision ID).
  * The `Manifest Relative Path` field is used when you need to upload your manifest to the app each time and it is stored under source control version.
  * The `Relative path to command output file` field identifies the location where the last command response will be stored (json file).
  * Select `Advanced...` to view the `When instance fails` drop-down list, which includes the following options:
    * Fail build
    * Mark build unstable
    * Ignore failure

#### Wait for Job Completion ####

This step is used to synchronize commands that were executed with the `Execute asynchronously` flag. We are experimenting with this feature and gathering user feedback.

FAQ
---

###How do I embed an environment variable into Qubell steps?###
All steps support environment variables and all fields support macros. Use the form `${ENVIRONMENT_VARIABLE_NAME}` to inject a value into the step. 
Note that if you use it in json as a string variable you should surround it with double qoutes in case, if you need to embed object you should worry about 
all quotes yourself, keeping valid json.

###How do I handle Returned Values (aka Instance Properties)?###
We suggest using `Relative path to command output file`, which is conveniently formatted for json. Use [./jq](http://stedolan.github.io/jq/) for parsing after 
the Qubell step, which is demonstrated below. 


    JQ=bin/jq  # jq alias
    JSON_OUTPUT=returns.txt                                  # Qubell step output file
    
    removeq () {                                             # Removes double quotes from stdin
      sed -e 's/^"\(.*\)"$/\1/'
    }

    getvalue () {
      ret=$(cat $JSON_OUTPUT | $JQ ".returnValues[\"$1\"] | if 
      type == \"array\" and length == 1 then .[] else . end " | removeq )
    }

    getvalue property_1
    echo "MY_PROPERTY_1=$ret" >qubell.env                    # Use with "Inject environment variables"


###Where I can find additional information?###
Please contact [Qubell Support](https://qubell.zendesk.com/hc/en-us) or review the [issues](https://github.com/qubell/contrib-jenkins-qubell-plugin/issues) page.

Contributors
------------

We welcomes contributors to the Jenkins plug-in for Qubell Platform. This plug-in is distributed under an Apache License (see [Licence](LICENSE)). If you would 
like to implement or fix code, please create a pull request to `master` branch. To suggest any improvements or report bugs, create [an issue](https://github.com/qubell/contrib-jenkins-qubell-plugin/issues/new).
