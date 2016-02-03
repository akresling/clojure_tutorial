(ns clojure_tutorial.controllers.routes
  (:require [clojure.string :refer [trim]]
            [compojure.core :refer [defroutes GET POST]]
            [ring.util.response :as ring]
            [clojure_tutorial.views.pages :as pages]
            [clojure_tutorial.views.layouts :as layouts]
            [clojure_tutorial.models.mortgage :as mort]))

(defn name_page [name]
  (pages/your_name_page name))

(defn show_results
  [principle amort interest]
  (let [p (Integer/parseInt principle)
        a (Integer/parseInt amort)
        i (Integer/parseInt interest)]
  (pages/res_page (format "%.2f" (mort/calculate p a i)))))

(defroutes routes
           (GET "/" [] (pages/main_page))
           (GET "/name/:name" [name] (name_page name))
           (GET "/calc" [] (pages/mort_calc_page))
           (POST "/result" {params :params} (let [principle (get params "principle")
                                                  amort (get params "amortization")
                                                  interest (get params "interest")]
                                              (show_results principle amort interest))))
