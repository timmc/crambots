(defproject crambots "1.0.0-SNAPSHOT"
  :description "TimMc's crosscram bots"
  :url "https://github.com/timmc/crambots"
  :license {:name "Eclipse Public License v1.0"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [org.timmc/handy "1.2.0"]]
  :dev-dependencies [[lein-git-deps "0.0.1-SNAPSHOT"]]
  :git-dependencies [["https://github.com/baznex/crosscram.git"
                      "migrate-api"]])
