## Welcome To Clojure
This tutorial is meant to teach you to make a VERY basic web app in clojure. Clojure is a functional programming language, with a "Lisp" syntax. If you've only ever worked with object oriented languages like Java, Ruby, or Python, this might feel very new for you. The idea of functional programming, specifically in Clojure, is that everything is a function. By everything, I really do mean everything. We will see in the code and below more about what I mean by that.

## Setup 
In an attempt to make this tutorial available to as many people as possible, I have provided instructions on how to setup up a working Clojure environment on both Windows and Mac OSX systems. 


### Install Java
Clojure runs in the JVM, so it needs a working version of Java.

#### Windows
[At least Version 1.6](http://www.oracle.com/technetwork/java/javase/downloads/index.html)

#### Mac OSX
[At least Version 1.6](http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html).

### Install Leiningen 
It is infinitely helpful and will help us with the rest of this tutorial. 

#### Windows
[Leiningen Manages Clojure Projects.](http://leiningen-win-installer.djpowell.net/)

#### Mac OSX
Easiest to use [Homebrew](http://brew.sh/).

```
brew install leiningen
```

### Text Editor
Pick your favourite editor or IDE. If you can effectively work in it there is no problem. If you are using Eclipse, you can use a plugin called [CounterClockwise](http://doc.ccw-ide.org/documentation.html#_install_counterclockwise). If you are using IntelliJ, you can try a plugin called [Cursive](https://cursive-ide.com/userguide/).

If you dont have either, or want to try something new, you can try a good clojure based IDE called [LightTable](http://lighttable.com/).


### Install git
If you don't have git, I couldn't recommend it enough. It is a source code management system. We are gonna be using it to grab the finished source code and the starting skeleton. [To install Git](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git). 


## Getting Started
#### Clone the source code

```
git clone https://github.com/akresling/clojure_tutorial.git
```
Switch the branch to the skeleton. 

```
git checkout skeleton
```

This has several sections removed. Below we will cover how to fill in the blanks and complete the functionality of the master version. 

Some sections have not been removed because they function primarily as utilities to save time or make some sections easier.

## First Steps
We will be using a Clojure framework called Compojure. Long story short, you can set up routes and their methods, it will then run a function to produce a response. This is the job of a controller. 


#### First Route
We will define the routes in a file called routes.clj using a `defroutes` macro from Compojure. 

Our very first route will be to the welcome page.

```
(defroutes routes
           (GET "/" [] (pages/main_page)))
```
As seen in your skeleton, the pages.clj file has an empty function for main_page. Our layouts.clj file has something we can use.

The function `common` takes a title and a body. It will construct a basic html core, fill it with the body, and set the title of the page to the one provided.

Lets make our small main page. 

```
(defn main_page []
  (layout/common "Welcome"
                 [:div {:class "container"}
                  [:h5 "This is your first clojure app!"]]))
```

This may look like a strange way of writing HTML, and it is very different. It uses a library called `Hiccup` to parse this data structure and render it as HTML.

#### Let's see what we made
If youre on mac, run the following command in the root directory of the project.

```
lein repl
```

If youre on windows, depending on your tool, there should be a way to initialize the repl on the project.clj file.

The REPL is a "READ-EVAL-PRINT LOOP". It allows you to write clojure code which will execute immediatly and print the results. You can include differently files and use the functions in them. This is very helpful for debugging and can be used to play with different ideas for solving problems. We will use it further later, but currently we are interested in running the `-main` function in the core.clj file. This will initialize our local server with the routes we wrote. 

In the repl, simply write:

```
(-main)
```
Another way of running the main function would by just writing in the terminal: 

```
lein run
```

## GET Request and Query Parameters
How can we pass a query parameter to our GET request. It's simple, we just include the variable we want in the route.

Let's make a request where we pass in our name, and use it in the response.

Add the following to the routes: 

```
(GET "/name/:name" [name] (name_page name))
```
The `/name` is the request path, `:name` will be where our own input is, and a in the square brackets, `[name]` we have set it into a variable. We will be using the `name_page` controller function that is already provided. That function in turn uses the html response page from the pages.clj file.

That's it thats all, now run the main function again and go to:

```
localhost:8080/name/YourName
```

## Forms and Calculations
I have included a form that we will use to make a basic mortgage calculator. We need to make a route to the form.

```
(GET "/calc" [] (pages/mort_calc_page))
```
Adding this to the route will allow you to get to the mortgage calculator form. The form is already provided but we will look at a couple of points from it.

```
(form/form-to [:post "/result"] ... )
```
This function creates a form which will send a POST request to the `/result` path.  

You have three different inputs, Principle, Amortization, and Interest.

```
(form/text-area "principle")
```
This is an input text area for principle, we have the same thing for all three inputs. 

#### Result POST Request
We now need to make a route for our `/result` path. We need to be able to retrieve our three input values and pass them into a function for doing the calculation and printing the output. 

Let's see how it's done and we will discuss it. 

```
(POST "/result" {params :params} 
		(let [principle (get params "principle")
              amort (get params "amortization")
              interest (get params "interest")]
       	(show_results principle amort interest)))
```
Here we are doing a couple of new things. First off, we are using the following statement: `{params :params}`. This pulls the parameters out of the request map, and put them into a `params` variable.

Now what is a `let` function. It is a way of setting a temporary variable within its scope. You can then use it anywhere in the scope, but it disappears after the `let` function closes. The section within the square brackets basically says we will get from the map `params` the value at key `principle` and place it into a variable called `principle`.

We will then use there variables in a `show_results` functions. This functions will parse the integers out of Strings, and pass it into a calculate function. To parse integers you can actually use the Java library function. The way to call it is :

```
(Integer/parseInt principle)
```

We will use another let statement to parse all of the values into integers. We will then pass these values into a calculate function, and pass a formatter version of the output to a results pages.

It will look like this :

```
(defn show_results
  [principle amort interest]
  (let [p (Integer/parseInt principle)
        a (Integer/parseInt amort)
        i (Integer/parseInt interest)]
  (pages/res_page (format "%.2f" (mort/calculate p a i)))))
```

The square brackets after the defined function name are the input variables for the function.

Now let us work on the calculate function. In reality you can make it be however you like, but we are gonna port the following Java code : 

```
interest = interest/12/100;
amortization = amortization*12;
double power = Math.pow((1+interest), ((-1)*amortization));
double monthly = (interest*principle)/(1-power);
return monthly;
```

Although initially it may not seem simple, that code in Clojure can be done in the following way :

```
(defn calculate
  [principle amort interest]
  (let [in (/ (/ interest 12) 100)]
    (/ (* in principle)
       (- 1 (Math/pow
              (+ 1 in)
              (* -1 (* amort 12)))))))
```

Now that we have all that together, the final part left is the results page. We will make a very simple one. 

```
(defn res_page [result]
  (layout/common "Result"
                 [:div {:class "container result"}
                  [:h4 (str "You will have to pay " result)]]))
```

### Congratulations
You've just made your first Clojure web app. I hope it has sparked some interest for functional programming in you. If you want to learn more, I will put the links for some videos discussing functional programming concepts, as well as the libraries that we used in this tutorial. Good luck, and Have Fun!

#### More Functional Programming
[Rich Hickey (creator of Clojure) speaking about Clojure](https://www.youtube.com/watch?v=VSdnJDO-xdg)

[Rich Hickey on Values](https://www.youtube.com/watch?v=-6BsiVyC1kM)

[Uncle Bob Speaking about State and Functional Programming](https://www.youtube.com/watch?v=7Zlp9rKHGD4)

#### Libraries Used
[Compojure - Routing](https://github.com/weavejester/compojure)

[Ring - Running http server](https://github.com/ring-clojure)

[Hiccup - HTML Templating](https://github.com/weavejester/hiccup)


## License

Copyright © 2016 Akresling
