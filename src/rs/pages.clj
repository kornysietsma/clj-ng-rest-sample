(ns rs.pages
  (:require [hiccup.core :as h]
            [hiccup.page :refer [html5]]))

(defn index []
  (h/html (html5 [:h1 "Rest sample"]
                 [:article
                  [:a {:href "things"} "Show all things"]]
                 [:article
                  [:a {:href "healthcheck"} "health check"]])))