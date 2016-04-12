logLevel := Level.Warn

addSbtPlugin("com.eed3si9n"      % "sbt-assembly"    % "0.14.2")  // Packaging
addSbtPlugin("de.heikoseeberger" % "sbt-header"      % "1.5.1")   // File header automation for copyright
addSbtPlugin("org.brianmckenna"  % "sbt-wartremover" % "0.14")    // Code linting
addSbtPlugin("org.scalariform"   % "sbt-scalariform" % "1.6.0")   // Formatting