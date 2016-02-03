(ns clojure_tutorial.views.layouts
  (:require [hiccup.page :as h]))

(defn common [title & body]
  (h/html5
    [:head
     [:title title]
     (h/include-css "/css/style.css")]
    [:body
     [:div {:id "header" :class "container"}
      [:h2 {:class "container"} title]]
     [:div {:class "container content"} body]
     [:div {:class "container"}
      [:a {:href "/"} "Back to home page"]
      [:a {:href "/calc"} "Mortgage Calculator"]]]))

(defn four_o_four []
  (common "Page Not Found"
          [:div {:class "container" :id "four-oh-four"}
           [:p "The page you are looking for could not be found"]]))
