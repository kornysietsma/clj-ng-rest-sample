(ns rs.pages
  (:require [hiccup.core :as h]
            [hiccup.page :refer [html5]]))

(defn index []
  (h/html (html5 [:h1 "Rest sample"]
                 [:article
                  [:a {:href "index.html"} "Angular.js app"]]
                 [:article
                  [:a {:href "things"} "Show all things (json)"]]
                 [:article
                  [:a {:href "healthcheck"} "health check (json)"]])))

