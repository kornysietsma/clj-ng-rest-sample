; ensures that reloading the system doesn't run tests
(when (running-in-repl?)
  (change-defaults :check-after-creation false))