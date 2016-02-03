(ns clojure_tutorial.models.mortgage)

(defn calculate
  [principle amort interest]
  (let [in (/ (/ interest 12) 100)]
    (/ (* in principle)
       (- 1 (Math/pow
              (+ 1 in)
              (* -1 (* amort 12)))))))

