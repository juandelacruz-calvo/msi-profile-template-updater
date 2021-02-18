# MSI Afterburner Profile template updater

MSI afterburner loses the profiles as soon as you plug a new graphic card in your computer. 

This application has been created to template configurations based on graphic cards  by model and/or name with the intention of reducing the hassle of having to reconfigure all your graphic cards one by one.

## Usage

1. compile the project using Gradle
``` gradle build```
2. Once the Jar file has been generated execute it using the next command line:

```java -jar msi-profile-template-updater <path to templates> <path to profiles> ```

The templates have to end in `.template`

They can include VENdor + DEVice information or also SUBSYStem

For example 
1. `VEN_1002&DEV_66AF.template` will replace the configuration of all your AMD Radeon 5700 XT in one go to use your template
2. `VEN_1002&DEV_66AF&SUBSYS_081E1002.template` will do the same but only for those which share the same ID (same exact brand and model)