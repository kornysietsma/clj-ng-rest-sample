(ns rs.web
  (:require [compojure.core :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [rs.resources :as resources]
            [hiccup.core :as h]
            [hiccup.page :refer [html5]]))

(defroutes app-routes
  (GET "/healthcheck" [] resources/healthcheck)
  (GET "/things" [] resources/things)
  (route/not-found {:status 404 :body "nothing to see here, move along"})
  ;; the rest isn't really about REST, but lets us serve static and dynamic content
  (route/resources "/")
  (GET "/" [] (h/html (html5 [:h1 "Things!"]
                             [:article
                              [:a {:href "things"} "Things?"]]
                             [:article
                              [:a {:href "healthcheck"} "health check"]]))))

(def app
  (-> (handler/api app-routes)))

(defn init []
  (println "Starting server"))

(defn destroy []
  (println "Stopping server"))
