<img align="right" src="https://media.discordapp.net/attachments/919010462476152832/986411438954385438/MC_Trade_Bot.png" height="200" width="200">        


 <img alt="GitHub" src="https://img.shields.io/badge/Discord%20Bot-7289DA?style=for-the-badge&logo=discord&logoColor=white">         


<img alt="GitHub" src="https://img.shields.io/github/languages/code-size/udu3324/Typsnd">         


<img alt="GitHub" src="https://img.shields.io/github/issues/udu3324/MC-Trade-Bot">        


<img alt="GitHub" src="https://img.shields.io/github/license/udu3324/MC-Trade-Bot">      


# MC Trade Bot
MC Trade Bot is a QOL discord bot for traders on MC. You can easily report scammers and check for their status. The bot stores UUIDs which ignores player IGN changes.

## Examples and Usage
I play on MC servers that don't have trade plugins. This means that I would have to drop first or get a middle man to trade. Doing it could cause me to get scammed, so I made a bot that logs reports and lets people check if someone is a scammer.

![reporting someone](https://media.discordapp.net/attachments/956773599644090379/986413538287443998/unknown.png)  
![accepting report](https://media.discordapp.net/attachments/956773599644090379/986413900645945364/unknown.png)  
![rejecting report](https://media.discordapp.net/attachments/956773599644090379/986414488611881030/unknown.png)  
![checking result is good](https://media.discordapp.net/attachments/956773599644090379/986414697064579092/unknown.png)  
![checking result is maybe](https://media.discordapp.net/attachments/956773599644090379/986414559231348807/unknown.png)  
![checking result is bad](https://media.discordapp.net/attachments/956773599644090379/986414910068097044/unknown.png)  
![deleting accepted report](https://media.discordapp.net/attachments/956773599644090379/986414128216281148/unknown.png)


## Setup the Bot
0. Make sure you have Java 8 Development Kit and Intellij installed
1. Clone the repository
2. Import it into Intellij
3. Run the Gradle sync task (it should do it automatically)
4. Configure the config (in com.udu3324.main.Config)
5. Run the bot using Gradle run task (tasks>application>run)
6. (optional)
7. If you want a portable jar to run in terminal
8. Run the Gradle task runShadow

## Config
```java 
 // Bot Token 
 public static String token = "";    
 
 // The Role Allowed To Accept, Reject, And Delete Reports 
 public static String staffRoleID = "";    
 
 // Channels That The Commands Will Work In 
 public static String reportChannelID = ""; 
 public static String checkChannelID = "";   
  
 // Command Prefix 
 public static final String prefix = ">";  
``` 

## Commands
``` 
Member Commands 
>help - shows help embed 
>check [player-ign/uuid] - checks if they are a scammer 
>report [player-ign/uuid] - [what they stole] - [youtube link] - creates a report 
>ping - shows time delay between you and the bot     

Staff Commands 
>accept [ign/uuid] - accepts the scammer report 
>reject [ign/uuid] - rejects the scammer report 

(in case if you accidentally accepted a bad scammer report)

>delete [ign/uuid] - deletes a confirmed scammer report
```   

## Contribution
You can contribute to MC Trade Bot by creating  [issues](https://github.com/udu3324/MC-Trade-Bot/issues/new/choose) and  [pull requests](https://github.com/udu3324/MC-Trade-Bot/compare).