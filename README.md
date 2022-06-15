<img align="right" src="https://media.discordapp.net/attachments/919010462476152832/986411438954385438/MC_Trade_Bot.png" height="200" width="200">  

<img alt="GitHub" src="https://img.shields.io/github/license/udu3324/MC-Trade-Bot">  

# MC Trade Bot
MC Trade Bot is a QOL tool for traders on MC. You can easily report scammers and check for their status.

## Examples and Usage
I play on MC servers that don't have trade plugins. This means that I would have to drop first or get a middle man. Doing that is risky. I made a bot that logs reports and lets people check if someone is a scammer.
![reporting someone](https://media.discordapp.net/attachments/956773599644090379/986413538287443998/unknown.png)
![accepting report](https://media.discordapp.net/attachments/956773599644090379/986413900645945364/unknown.png)
![rejecting report](https://media.discordapp.net/attachments/956773599644090379/986414488611881030/unknown.png)
![checking result is good](https://media.discordapp.net/attachments/956773599644090379/986414697064579092/unknown.png)
![checking result is maybe](https://media.discordapp.net/attachments/956773599644090379/986414559231348807/unknown.png)
![checking result is bad](https://media.discordapp.net/attachments/956773599644090379/986414910068097044/unknown.png)
![deleting accepted report](https://media.discordapp.net/attachments/956773599644090379/986414128216281148/unknown.png)

## Bot Documentation

Config (in com.udu3324.main.Config)
```java  
// Bot Token  
public static String token = "";  
  
// The Role Allowed To Accept, Reject, And Delete Reports  
public static String staffRoleID = "000000000000000000";  
  
// Channels That The Commands Will Work In  
public static String reportChannelID = "000000000000000000";  
public static String checkChannelID = "000000000000000000";  
  
// Command Prefix  
public static final String prefix = ">";
```  
Commands
```  
Member Commands  
>help - shows help embed  
>check [player-ign/uuid] - checks if they are a scammer  
>report [player-ign/uuid] - [what they stole] - [youtube link] - creates a report  
>ping - shows time delay between you and the bot   
  
Staff Commands  
>accept [ign/uuid] - accepts the scammer report  
>reject [ign/uuid] - rejects the scammer report  
```  

## Contribution
You could contribute to MC Trade Bot by creating  [issues](https://github.com/udu3324/MC-Trade-Bot/issues/new/choose) and  [pull requests](https://github.com/udu3324/MC-Trade-Bot/compare).