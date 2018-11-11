# Real Estate Finder

- application is just SpringBoot Demo

## What App does

- is parsing web page with real estates in Slovakia in Kosice and filter them out trying to get less links that points to real estates that are selling people not offices or companies.
- of course there are still false positive results but number of all results is after application run possible to check manually. Count of links are significantly lower.
- false positive results happens because filtering rule is simple ( for demo purpose it is ok )

## Technical point of view

- [Spring Boot](http://spring.io/projects/spring-boot) application (2.1.0)
- Parsing with [Selenium](https://www.seleniumhq.org) with [PhantomJS Driver](http://phantomjs.org) (browser less) and [Jsoup](https://jsoup.org)
- Spring Boot @Autowired can be avoided using thank to Spring 5 andthanks to  [Lombok](https://projectlombok.org) @AllArgsConstructor there is no need for constructor. See [RealEstateService](https://github.com/peterszatmary/real-estate-finder/blob/master/src/main/java/com/reality/finder/realityfinder/RealEstateService.java) and/or [RealEstateController](https://github.com/peterszatmary/real-estate-finder/blob/master/src/main/java/com/reality/finder/realityfinder/RealEstateController.java).

## Result

- html file with real estate links already filtered

## Prerequisites

- you need to install PhantomJS Driver and Selenium

```shell

# selenium
sudo apt-get install software-properties-common
sudo add-apt-repository ppa:deadsnakes/ppa
sudo apt-get update
sudo apt-get install python3.6
sudo apt-get install python3-pip
pip install selenium

# phantomjs
sudo apt-get update
sudo apt-get install build-essential chrpath libssl-dev libxft-dev
sudo apt-get install libfreetype6 libfreetype6-dev
sudo apt-get install libfontconfig1 libfontconfig1-dev
cd ~
export PHANTOM_JS="phantomjs-2.1.1-linux-x86_64"
wget https://bitbucket.org/ariya/phantomjs/downloads/$PHANTOM_JS.tar.bz2
sudo tar xvjf $PHANTOM_JS.tar.bz2
sudo mv $PHANTOM_JS /usr/local/share
sudo ln -sf /usr/local/share/$PHANTOM_JS/bin/phantomjs /usr/local/bin
phantomjs --version

```

## Running the demo

- Demo is REST application so is running in your favourite browser.
- app is running on port 8070.
- because this is just demo application inputs are not checked.

```shell
http://localhost:8070/search/13/16/5
```

- first number is page number from (13)
- second number is page number to (16)
- third number is html file target postfix (5)
