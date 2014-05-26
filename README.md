Jenkins Plug-in for Qubell Platform
===================================

[![Build Status](https://travis-ci.org/qubell/contrib-jenkins-qubell-plugin.png?branch=master)](https://travis-ci.org/qubell/contrib-jenkins-qubell-plugin)

Functionality
-------------

Jenkins Plug-in for [Qubell Platform](http://qubell.com) allows to launch and manipulate Qubell instances as a part of a Jenkins build job.

This plug-in provides the following functionality:

* Configure access credentials as a part of global configuration.
* Launch a new instance from a manifest located in the source repository.
* Execute a custom job on the newly-launched instance.
* Destroy the newly-launched instance.
* Execute a custom job on an instance specified by instance ID.
* Fail the build job if any of the actions above is not finished after a configurable timeout.
* Gather instance properties and job execution results in a JSON file to be consumed by the rest of the pipeline.
* Macros resolve in all steps and fields, in form of `${PARAM_NAME}`
* Application and Environment hints as you type
* (Experimental) Async execution to speedup parallel instance execution and command processing

Compatibility
-------------

This plug-in uses [Qubell Platform API v. 1.3+](http://docs.qubell.com/api/contents.html) and can be used with Qubell Platform R19
and above, either Express or Enterprise editions.

Getting Started
---------------

Install, configure jobs with Qubell steps, enjoy... Details are following.

### Install and setup ###

This part should be done only once.

1. Install Plugin from [Releases](https://github.com/qubell/contrib-jenkins-qubell-plugin/releases)
2. Configure credentials (1)
   - Go to `Manage Jenkins` -> `Configure System`
   - Scroll to `Qubell Account Configuration`
   - Define and check: `API Url`, `Login`** and `Password` (2)
   - Check `Status Polling Interval`(3), increase if appropriate
  
*(1) -: Qubell steps will not function properly, if credentials are wrong*  
*(2) -: We suggest to create a deployment bot and use its credentials here*  
*(3) -: This interval corresponds how often jenkins will poll Qubell to get updates.*

### Using steps ###

Now you are ready to configure your job. To implement common: Launch Instance -> Run Command -> Destroy. Refere following steps.

#### Launch Application Instance ####

This step launces instance of selected `Application Id` in selected `Environment Id` (or default if kept empty). You may type in Application Name or Environment Name and UI will suggest ids that fit your input.

In most cases you'll need one instance per job, and this step will store launched instance Id in context.

Additionally you may configure:

`Timeout` in seconds allows to limit launch time.

`Extra parameters` allows to tune your job preciecly with any missed parameters including revision id, please refere Compatible Qubell Api.

`Manifest Relative Path` is used when you need to upload manifest each time to you app, this is convinient when you store you manifest under Source Control Version. If not specified Manifest's will be used.

`Relative path to command output file` this is file where last response of command will be stored, this is a json file.

`When instance fails` - advance option to distinguish what to do if Launch fails, on default it will fail a job.

#### Run Command ####

This step execute `Name`-command of context instance or if specified of `Custom Instance Id`. The rest of parameters are the same to "Launch Application Instance" step.

*Note: `Execute asynchronously` is experimental, refer [FAQ] for "Wait for job completion" step*

#### Destroy Instance ####

This step destroys context instance or if specified of `Custom Instance Id`. `Timeout` and `When instance fails` here allows to speedup job time and do not fail a job if it is not as important.

Screenshot
----------
![Build steps to launch and execute a command](https://raw.github.com/wiki/qubell/contrib-jenkins-qubell-plugin/build-step-config.png)

FAQ
---

Q: How to embed environment variable into Qubell steps  
A: All steps support it, use form of `${ENVIRONMENT_VARIABLE_NAME}`, to inject desired value into step. All fields should support macroses. Please note, if you use it in json as a string variable you should surround it with double qoutes in case, if you need to embed object you should worry about all quotes yourself, keeping valid json.

Q: How to deal with Returned Values (aka Instance Properties)  
A: We suggest to use `Relative path to command output file`, since it is JSON formated, it is convinient ot use [./jq](http://stedolan.github.io/jq/) for parsing after qubell step. Following is the snippet how to use it now. We are looking for a ways how to make it simple from the box. 

```bash
JQ=bin/jq  # jq alias
JSON_OUTPUT=returns.txt  # Qubell step output file

#removes double quotes from stdin
removeq () {
  sed -e 's/^"\(.*\)"$/\1/'
}

getvalue () {
  ret=$(cat $JSON_OUTPUT | $JQ ".returnValues[\"$1\"] | if type == \"array\" and length == 1 then .[] else . end " | removeq )
}

getvalue property_1
echo "MY_PROPERTY_1=$ret" >qubell.env  # File that is easy to use with "Inject environment variables"
```

Q: What is "Wait for job completion" step  
A: This step is used to syncronise commands that were executed with `Execute asynchronously` flag. We are experimenting with this and waiting you feedbacks.

Q: Where I can find exhaustive list of features and explanation?  
A: Unfortunatelly, we are in progress with documentation and will release as soon as, please contact support or use [issues](https://github.com/qubell/contrib-jenkins-qubell-plugin/issues) to get missed information.

Contrib
-------

This plugin welcomes contributors and is distributed unders Appache Licence, see [Licence](LICENSE)

If you'd like to implement or fix something, please create a pull request to `master` branch.

To suggest any improvements or report bugs, create [an issue](https://github.com/qubell/contrib-jenkins-qubell-plugin/issues/new).
