(ns clojure_tutorial.examples)

(defn squares
  [n]
  (map (fn [x] (* x x)) (take (+ n 1) (range))))
