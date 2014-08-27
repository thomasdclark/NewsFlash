#NewsFlash
Positivity rankings for your favorite news sources

##Functionality
NewsFlash is an application for analyzing online news sources and ranking them based on the positivity of their content.  The application receives a user input file containing the links to the RSS feeds of various news feeds.  It then uses the RSS feeds to link to and parse the webpages of the articles currently being featured on the news sites.  The content of these news articles is then crawled to count key positive and negative words, which NewsFlash uses to create an overall ranking of the news sites.  Users can customize NewsFlash based on the positive and negative words that it uses, as well as the news sources that it ranks.  The goal of NewsFlash is to allow users to hopefully see how their favorite news sources compare to each other based on the positivity of their content, and to reflect on how this can affect the overall take away and meaning of the content.

![NewsFlash](https://raw.githubusercontent.com/thomasdclark/NewsFlash/master/example/graph_example.png)

##Dependencies
Need to be downloaded from respective sites prior to use:
* [OSU CSE Components](http://web.cse.ohio-state.edu/software/common/doc/)
* [Charts4J](https://code.google.com/p/charts4j/)