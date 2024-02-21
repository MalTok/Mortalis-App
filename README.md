# MORTALIS - necrology web app

> This web app allows you add and find necrologies - share funeral details, share private memory of late relative, search through other necrologies, light a virtual candle or write condolences.
> Live demo [_here_](https://).

## Content of project

- [General Info](#general-information)
- [Technologies Used](#technologies-used)
- [Features](#features)
- [Screenshots](#screenshots)
- [Setup](#setup)
- [Usage](#usage)
- [Project Status](#project-status)
- [Room for Improvement](#room-for-improvement)
- [Sources](#sources)
- [Contact](#contact)

## General Information

<details>
<summary>Click here to see general information about <b>Mortalis</b>.</summary>
Mortalis is designed strictly for polish users, so it has visual features used commonly in polish obituaries.
The app lets you add necrologies of your late realtives, light them multiple virtual candles in memoriam, write condolences, search through other necrologies. It includes API, admin / moderator management of content. It's the place where you can mourn your close ones, leave few words of memories you shared with the late person etc.
</details>

## Technologies Used

<div>
  <div>
    <a href="https://www.java.com/">
      <img src="https://img.shields.io/badge/Java-%23F80000?style=for-the-badge&logo=oracle" alt="red button oracle java"/>
    </a> - version 17
  </div>
  <div>
    <div>
      <a href="https://www.thymeleaf.org/">
        <img src="https://img.shields.io/badge/Thymeleaf-%23005F0F?style=for-the-badge&logo=thymeleaf"/>
      </a> - version 3.2.1
    </div>
    <div>
      <ul>
        <li>Thymeleaf layout dialect - version 3.3.0</li>
        <li>Thymeleaf extras spring security - version 3.1.2</li>
      </ul>
    </div>
  </div>
  <div>
    <div>
      <a href="https://spring.io/projects/spring-boot">
        <img src="https://img.shields.io/badge/Spring%20Boot-%236DB33F?style=for-the-badge&logo=springboot&logoColor=black"/>
      </a> - version 3.2.2
    </div>
    <div>
      <ul>
        <li>Spring Data JPA</li>
        <li>Spring Security</li>
        <li>Spring Validation</li>
        <li>Spring Web</li>
        <li>Spring Test</li>
        <li>Spring Mail</li>
      </ul>
    </div>
  </div>
  <div>
    <a href="https://www.liquibase.org/">
      <img src="https://img.shields.io/badge/Liquibase-%232962FF?style=for-the-badge&logo=liquibase&logoColor=black"/>
    </a> - version 4.24.0
  </div>
  <div>
    <p>H2 Database - version 2.2.224</p>
  </div>
  <div>
    <a href="https://www.mysql.com/">
      <img src="https://img.shields.io/badge/MySQL-%234479A1?style=for-the-badge&logo=mysql&logoColor=black"/>
    </a>
  </div>
  <div>
    <p>Lombok - version 1.18.30</p>
  </div>
  <div>
    <img src="https://img.shields.io/badge/JavaScript-%23F7DF1E?style=for-the-badge&logo=javascript&logoColor=black"/>
  </div>
  <div>
    <a href="https://jquery.com">
      <img src="https://img.shields.io/badge/jQuery-0769AD?style=for-the-badge&logo=jquery&logoColor=white"/>
    </a>
  </div>
  <div>
    <img src="https://img.shields.io/badge/HTML-%23E34F26?style=for-the-badge&logo=html5&logoColor=black"/>
  </div>
  <div>
    <img src="https://img.shields.io/badge/CSS-%231572B6?style=for-the-badge&logo=css3&logoColor=black"/>
  </div>
  <div>
    <a href="https://getbootstrap.com">
      <img src="https://img.shields.io/badge/Bootstrap-563D7C?style=for-the-badge&logo=bootstrap&logoColor=white"/>
    </a> - version 5.3.2
  </div>
  <div>
    <a href="https://fontawesome.com/">
      <img src="https://img.shields.io/badge/FontAwesome-%23528DD7?style=for-the-badge&logo=fontawesome&logoColor=black"/>
    </a>
  </div>
</div>

## Features

Ready features:

- <b>Necrology adding</b>:
<ul>
  <li>form with validated required fields</li>
  <li>personal data of late person, funeral details, picking kinship from database list and other minor features</li>
  <li>ability to check for automaticaly remove necrology after 14 days</li>
  <li>dynamic preview of necrology</li>
  <li>verification with activation code via e-mail message</li>
</ul>

- <b>Candle adding:</b>
<ul>
  <li>form with validated required fields</li>
  <li>ability to light a candle for 30 days</li>
  <li>verification with activation code via e-mail message</li>
</ul>

- <b>Condolences adding:</b>
<ul>
  <li>form with validated required fields</li>
  <li>ability to add text and signature of person adding</li>
  <li>verification with activation code via e-mail message</li>
</ul>

- <b>showing only previously activated necrologies, candles and condolences in entire service</b>

- <b>showing last 10 added necrologies on main page as a basic information preview</b>

- <b>searching through necrology database</b>

- <b>admin / moderator account for simple moderation of added necrologies and condolences (currently available only removing option)</b>

- <b>automaticaly removed records from database when:</b>
<ul>
  <li>expired (necrologies, candles)</li>
  <li>not activated by user (necrologies, candles and condolences)</li>
</ul>

- <b>simple API for potential local services (ability to get data such as total necrologies number in database, list of necrologies with particular name or place of funeral, single necrology by known Id numer, etc., in JSON format)</b>

- <b>added instruction, FAQ, website regulations</b>

## Screenshots

![Example screenshot](./screenshots/screenshot1.png)

## Setup

What are the project requirements/dependencies? Where are they listed? A requirements.txt or a Pipfile.lock file perhaps? Where is it located?

Proceed to describe how to install / setup one's local environment / get started with the project.

## Usage

<details>
  <summary>Click here to see possible usage of <b>Mortalis</b>.</summary>
  This app can be used by any funeral home or by country-wide service managing obituaries. Implemented API can be used by various local services to gather info and statistics about locals who recently passed away.
</details>

## Project Status

Project is: _in progress_<br/>
Working on new, possible features.

## Room for Improvement

Room for improvement:

<ul>
  <li>Admin / moderator panel - add more options like ability to edit necrologies or condolences</li>
</ul>

To do:

<ul>
  <li>Adding payment option</li>
  <li>When payment will be added - add ability to sign up for normal users, free limit of necrologis per month per user - necrologies above limit will be charged (for example for funeral companies)</li>
</ul>

## Sources

- This project was inspired by the look and functionality of webpage [zmarli.info](https://zmarli.info/), which commercial on YT I saw some time ago
- All of the pictures used in this project come from [freepik.com](https://pl.freepik.com/) (authors: mdjaff, rawpixel.com)

## Contact

Feel free to contact me via GitHub!
