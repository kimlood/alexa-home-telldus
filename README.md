# Telldus Alexa Home Skill 
Integrates [Telldus Live](http://live.telldus.com/) with Alexa as a Smart Home Skill. 

## Setup
1. In the `Global.groovy` file, update it with the needed keys. See [Telldus documentation](https://api.telldus.com/keys/index) for more information 
1. Run gradle/gradlew uberjar to build a deployable package of alexa-home-telldus. This will generate a `alexa-home-telldus-1.0.jar` file that should be uploaded to AWS Lamda. Use "gradlew" if you don't have a gradle installation, simply open a command prompt (cmd), navigate to the folder contating this project, and execute: gradlew uberjar

1. Create an Alexa skill and Lambda Function by following [these instructions](https://developer.amazon.com/public/solutions/alexa/alexa-skills-kit/docs/steps-to-create-a-smart-home-skill) (with the modifications noted below). Note1: don't forget about the steps to create a secuirty profile, and also make sure to add the "Redirect URLs" from the Alexa skill configuration to the "Web Settings->Allowed Return URLs" for the security profile. Also, enter the values from the secuirty profile to the configuration of the Alexa skill (some of the more common problems users have..)
Note2: Since Alexa now is released in more countries, make sure where your lambda is hosted. From Amazon: "Make sure you’ve selected the N.Virginia for English (US) skills or the EU (Ireland) region for English (UK) and German skills. The region is displayed in the upper right corner. Providing your Lambda function in the correct region prevents latency issues" (This can change in the future, so please verify it with the amazon documentation.) 

    * The name of the Alexa: Telldus-Alexa
    * The name of the Lambda function: Telldus-Alexa-Service
	* Select Runtime: Java8
    * Select `lambda_basic_execution` for the Lambda role
	* For "Handler", enter: Telldus::handler
    * Select "Upload a .ZIP or JAR file" for "Code entry type", and upload `alexa-home-telldus-1.0.jar` that you created in the previous step. 
    * Add Trigger: "Alexa Smart Home"
  
## Usage
After completing setup of alexa-home-telldus, tell Alexa: "Alexa, discover my devices".

Example of commands for Alexa: 
- Alexa, turn off the `<device name>`
- Alexa, turn on the `<device name>`
- Alexa, set `<name>` to `<number>` percent
- Alexa, increase `<device name>` by `<number>` percent
- Alexa, decrease `<device name>` by `<number>` percent


History of commands to Alexa under `Menu / Settings / History` in the [web](http://echo.amazon.com/#settings/dialogs) or mobile app.
To view or remove devices that Alexa knows about, you can go to `Menu / Smart Home` in the [web](http://echo.amazon.com/#smart-home) or mobile app.
