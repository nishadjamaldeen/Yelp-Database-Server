CPEN 221 / Machine Problem 5

Restaurants, Queries and Statistical Learning
===

This machine problem is designed to allow you to explore multiple aspects of software construction:
+ managing complex ADTs;
+ multithreading and the client-server pattern;
+ query parsing and execution.

In addition to these aspects, the problem also touches upon rudimentary methods for statistical inference and learning.

### Background

For this machine problem, you will work with an excerpt from the [Yelp Academic Dataset](https://www.yelp.com/academic_dataset). Specifically, you will work with data (in [JSON](https://en.wikipedia.org/wiki/JSON) format) on restaurants, and this data includes information about some restaurants, reviews of the restaurants, and user information (for those contributing reviews).

You will use the dataset to create and maintain a simple in-memory database with restaurants, users and reviews. (Since the Yelp Academic Dataset does not contain details of business near UBC we are using information for restaurants near UC Berkeley or UCB!)

The given dataset is in the JSON format and you can use [JSON Processing project](https://jsonp.java.net) implementation of a framework for working with JSON in Java 8. You will see that the JSON Processing project suggests that you use a build manager called Maven for obtaining the relevant libraries and including them in your project. Alternatively, you can download the JAR files and include them in your `CLASSPATH` for compilation and for execution.

### Part I: Building a `RestaurantDB` datatype

The first part of this machine problem is to build a `RestaurantDB` datatype that loads the given Yelp dataset into memory and then supports some operations on the dataset.

At the minimum, this datatype should have a constructor that takes three `String`s as arguments: these `Strings` represent filenames. The first file is the list of restaurants, the second file is the list of reviews and the third file is the user list.

### Part II: Statistical Learning

> Look at the skeleton code in the package for `statlearning`.

In this part of the machine problem you will implement two approaches to statistical machine learning: one is an instance of unsupervised learning and the second is an instance of supervised learning. Statistical learning is an exciting area for computing today!

#### k-means Clustering

Suppose you are given a set of (x, y) coordinates, you may sometimes want to group the points into _k_ clusters such that no point is closer to the center point (centroid) of a cluster other than the one to which it is assigned. In the case of restaurants, this approach may allow us to group restaurants that are in the same neighbourhood even without knowing anything about the neighbourhoods in a city. _A similar approach is used to group similar products on online shopping services such as Amazon._

The k-means algorithm finds k centroids within a dataset that each correspond to a cluster of inputs. To do so, k-means clustering begins by choosing k centroids at random, then alternates between the following two steps:

1. Group the restaurants into clusters, where each cluster contains all restaurants that are closest to the same centroid.
2. Compute a new centroid (average position) for each non-empty cluster.

This [visualization](http://tech.nitoyon.com/en/blog/2013/11/07/k-means/) is a good way to understand how the algorithm works.

For the k-means clustering algorithm, you should implement a method that returns a `List` of `Set`s: each `Set` representing a cluster of restaurants. You should also implement a method that converts such a `List` to JSON format as illustrated by the JSON file `voronoi.json` in the directory `visualize`.

You can run the provided visualization method using `python` (Python 3) and the visualization is called a [Voronoi tesselation](https://en.wikipedia.org/wiki/Voronoi_diagram).

> One can visualize the tessalation produced by k-means clustering by writing the JSON formatted cluster information to `voronoi.json` in the `visualize` directory and then launch `visualize.py` as follows: `python3 visualize.py`
> For the curious, you can also see some Javascript in action here.

#### Least Squares Regression

As an instance of supervised learning, you will implement an algorithm for predicting the rating that a user may give to a restaurant.

By analyzing a user's past ratings, we can try to predict what rating the user might give to a new restaurant.

To predict ratings, you will implement simple least-squares linear regression, a widely used statistical method that approximates a relationship between some input feature (such as price) and an output value (the rating) with a line. The algorithm takes a sequence of input-output pairs and computes the slope and intercept of the line that minimizes the mean of the squared difference between the line and the outputs.

Implement the `getPredictor` method, which takes a user and a feature function (as well as the `RestaurantDB`), and returns a _function_ that predicts the users ratings.

One method of computing these values is by calculating the sums of squares, S<sub>xx</sub>, S<sub>yy</sub>, and S<sub>xy</sub>:

+ S<sub>xx</sub> = Σ<sub>i</sub> (x<sub>i</sub> - mean(x))<sup>2</sup>
+ S<sub>yy</sub> = Σ<sub>i</sub> (y<sub>i</sub> - mean(y))<sup>2</sup>
+ S<sub>xy</sub> = Σ<sub>i</sub> (x<sub>i</sub> - mean(x))(y<sub>i</sub> - mean(y))

After calculating the sums of squares, the regression coefficients, and R<sup>2</sup> (`r_squared`), which is an estimate of the quality of the predictor, are defined as follows:

+ b = S<sub>xy</sub> / S<sub>xx</sub>
+ a = mean(y) - b * mean(x)
+ R<sup>2</sup> = S<sub>xy</sub><sup>2</sup> / (S<sub>xx</sub> S<sub>yy</sub>)

Also implement the `getBestPredictor` method that takes a user and a list of feature functions and returns the _best_ predictor function (the one that results in the highest R<sup>2</sup> value).

Consider the following feature functions for this machine problem:
+ restaurant price scale
+ restaurant mean rating
+ restaurant location: latitude
+ restaurant location: longitude

In this machine problem, we will use a **functional interface** to pass and return functions but we could have also considered using [lambdas that Java 8 supports](https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html).

> To pass and return functions in this machine problem, you can have classes that implement the interface `LeastSquaresRegression` which contains a single method to be implemented `lsrf`. Different implementations of the interface will allow for different functions `lsrf`.
