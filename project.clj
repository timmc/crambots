(defproject crambots "1.0.0-SNAPSHOT"
  :description "TimMc's crosscram bots"
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [org.timmc/handy "1.2.0"]]
  :dev-dependencies [[lein-git-deps "0.0.1-SNAPSHOT"]]
  :git-dependencies [["https://github.com/baznex/crosscram.git"
                      "migrate-api"]])
