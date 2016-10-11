# alexa-home-telldus 2016 - Telldus Alexa Home Skill 
Integrates [Telldus Live](http://live.telldus.com/) with Alexa. 

## Setup
1. In the `Global.groovy` file, update it with the needed keys. See [Telldus documentation](https://api.telldus.com/keys/index) for more information 
1. Run gradle/gradlew uberjar to build a deployable package of alexa-home-telldus. This will generate a `alexa-home-telldus-1.0.jar` file that you should upload to AWS Lamda
1. Register with an OAuth provider, such as Login with Amazon.
    * Note the "Client ID" and "Client Secret", as you'll need those later
1. Create an Alexa skill and Lambda Function by following [these instructions](https://developer.amazon.com/public/solutions/alexa/alexa-skills-kit/docs/steps-to-create-a-smart-home-skill) (with the modifications noted below).
    * The name of the Alexa: Telldus-Alexa
    * The name of the Lambda function: Telldus-Alexa-Service
	* Select Runtime: Java8
    * Select `lambda_basic_execution` for the Lambda role
	* For "Handler", enter "Telldus::handler"
    * Select "Upload a .ZIP or JAR file" for "Code entry type", and upload alexa-home-telldus-1.0.jar` that you created in step 1. 
    * Leave the rest of the defaults alone, and click "Next"
    * Add Trigger: "Alexa Smart Home"
  
## Usage
After completing setup of alexa-home-telldus, tell Alexa: "Alexa, discover my devices".

Here is the table of possible commands to use to tell Alexa what you want to do:

To do this... | Say this...
--------------|------------
ON Commands |
 | Alexa, turn on `<Device Name>`
 | Alexa, start `<Device Name>`
 | Alexa, unlock `<Device Name>`
 | Alexa, open `<Device Name>`
 | Alexa, boot up `<Device Name>`
 | Alexa, run `<Device Name>`
 | Alexa, arm `<Device Name>`
OFF Commands |
 | Alexa, turn off `<Device Name>`
 | Alexa, stop `<Device Name>` (this one is tricky to get right)
 | Alexa, stop running `<Device Name>` (also very tricky)
 | Alexa, lock `<Device Name>`
 | Alexa, close `<Device Name>`
 | Alexa, shutdown `<Device Name>`
 | Alexa, shut `<Device Name>`
 | Alexa, disarm `<Device Name>`
DIM Commands | `<Position>` is a percentage or a number 1-10
 | Alexa, brighten `<Device Name>` to `<Position>`
 | Alexa, dim `<Device Name> to <Position>`
 | Alexa, raise `<Device Name>` to `<Position>`
 | Alexa, lower `<Device Name>` to `<Position>`
 | Alexa, set `<Device Name>` to `<Position>`
 | Alexa, turn up `<Device Name>` to `<Position>`
 | Alexa, turn down `<Device Name>` to `<Position>`

To see what Alexa thinks you said, you can see the command history under `Menu / Settings / History` in the [web](http://echo.amazon.com/#settings/dialogs) or mobile app.

To view or remove devices that Alexa knows about, you can go to `Menu / Smart Home` in the [web](http://echo.amazon.com/#smart-home) or mobile app.
