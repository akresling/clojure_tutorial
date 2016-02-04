(ns clojure_tutorial.controllers.routes
  (:require [clojure.string :refer [trim]]
            [compojure.core :refer [defroutes GET POST]]
            [ring.util.response :as ring]
            [clojure_tutorial.views.pages :as pages]
            [clojure_tutorial.views.layouts :as layouts]
            [clojure_tutorial.models.mortgage :as mort]))

(defn name_page [name]
  (pages/your_name_page name))

(defn show_results [])

(defroutes routes )
