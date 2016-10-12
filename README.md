# alexa-home-telldus 2016 - Telldus Alexa Home Skill 
Integrates [Telldus Live](http://live.telldus.com/) with Alexa. 

## Setup
1. In the `Global.groovy` file, update it with the needed keys. See [Telldus documentation](https://api.telldus.com/keys/index) for more information 
1. Run gradle/gradlew uberjar to build a deployable package of alexa-home-telldus. This will generate a `alexa-home-telldus-1.0.jar` file that you should upload to AWS Lamda

1. Create an Alexa skill and Lambda Function by following [these instructions](https://developer.amazon.com/public/solutions/alexa/alexa-skills-kit/docs/steps-to-create-a-smart-home-skill) (with the modifications noted below).
    * The name of the Alexa: Telldus-Alexa
    * The name of the Lambda function: Telldus-Alexa-Service
	* Select Runtime: Java8
    * Select `lambda_basic_execution` for the Lambda role
	* For "Handler", enter "Telldus::handler"
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
