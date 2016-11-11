(ns iworked.core
  (:require [iworked.parser :as p]
            [iworked.store :as s])
  (:gen-class))

(def location ".iworked.cljdata")

(defn -main
  [& args]
  (let
    [data (s/load-from location)]
    (s/save-to location (p/parse-word (first args)))
    ))

