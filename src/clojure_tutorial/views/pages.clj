(ns clojure_tutorial.views.pages
  (:require [hiccup.page :as h]
            [clojure_tutorial.views.layouts :as layout]
            [hiccup.form :as form]))

(defn main_page [] )

(defn res_page [result] )

(defn your_name_page [name]
  (layout/common "Welcome"
                 [:div {:class "name"}
                  [:h5 "Your name must be"][:h4 name]]))

(defn mort_calc_form []
  [:div {:class "mort_calc_form"}
   (form/form-to [:post "/result"]
                 (form/label "principle" "Enter a principle")
                 (form/text-area "principle")
                 (form/label "amortization" "Enter an amortization period")
                 (form/text-area "amortization")
                 (form/label "interest" "Enter an interest rate")
                 (form/text-area "interest")
                 (form/submit-button "Calculate"))])

(defn mort_calc_page []
  (layout/common "Mortgage Calculator"
                 (mort_calc_form)))




