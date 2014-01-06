(ns int.rs.simple.i-web
  (:require [midje.sweet :refer :all]
            [rs.simple.web :as web]
            [ring.mock.request :as mock]
            [cheshire.core :as cheshire]))

(defn is-json-str [expected]
  (chatty-checker [actual]
                  (= (cheshire/parse-string actual) expected)))

(facts "regarding the web api"
       (fact "you can get a health check"
             (web/app (mock/request :get "/healthcheck"))
             => (contains {:status 200
                           :body (is-json-str {"status" "ok"})})))

