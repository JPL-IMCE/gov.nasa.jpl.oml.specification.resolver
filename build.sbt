
import com.typesafe.sbt.packager.SettingsHelper
import sbt.Keys._
import sbt._
import gov.nasa.jpl.imce.sbt._
import gov.nasa.jpl.imce.sbt.ProjectHelper._

updateOptions := updateOptions.value.withCachedResolution(true)

import scala.io.Source
import scala.util.control.Exception._

import ProjectRefHelper._

lazy val core = Project("oml-resolver", file("."))
  .enablePlugins(IMCEGitPlugin)
  .settings(dynamicScriptsResourceSettings(Settings.name))
  .settings(IMCEPlugin.strictScalacFatalWarningsSettings)
  .settings(
    IMCEKeys.licenseYearOrRange := "2016",
    IMCEKeys.organizationInfo := IMCEPlugin.Organizations.omf,

    buildInfoPackage := Settings.namespace,
    buildInfoKeys ++= Seq[BuildInfoKey](BuildInfoKey.action("buildDateUTC") { buildUTCDate.value }),

    projectID := {
      val previous = projectID.value
      previous.extra(
        "build.date.utc" -> IMCEKeys.buildUTCDate.value,
        "artifact.kind" -> "generic.library")
    },

    IMCEKeys.targetJDK := IMCEKeys.jdk18.value,
    git.baseVersion := Settings.version,
    // include all test artifacts
    publishArtifact in Test := true,

    scalaVersion := Settings.versions.scala,

    SettingsHelper.makeDeploymentSettings(Universal, packageZipTarball in Universal, "tgz"),

    SettingsHelper.makeDeploymentSettings(UniversalDocs, packageXzTarball in UniversalDocs, "tgz"),

    packagedArtifacts in publish += {
      val p = (packageZipTarball in Universal).value
      val n = (name in Universal).value
      Artifact(n, "tgz", "tgz", Some("resource"), Seq(), None, Map()) -> p
    },
    packagedArtifacts in publishLocal += {
      val p = (packageZipTarball in Universal).value
      val n = (name in Universal).value
      Artifact(n, "tgz", "tgz", Some("resource"), Seq(), None, Map()) -> p
    },
    packagedArtifacts in publishM2 += {
      val p = (packageZipTarball in Universal).value
      val n = (name in Universal).value
      Artifact(n, "tgz", "tgz", Some("resource"), Seq(), None, Map()) -> p
    },

    resolvers += Resolver.bintrayRepo("jpl-imce", "gov.nasa.jpl.imce"),

    resolvers += "Artima Maven Repository" at "http://repo.artima.com/releases",
    scalacOptions in (Compile, compile) += s"-P:artima-supersafe:config-file:${baseDirectory.value}/project/supersafe.cfg",
    scalacOptions in (Test, compile) += s"-P:artima-supersafe:config-file:${baseDirectory.value}/project/supersafe.cfg",
    scalacOptions in (Compile, doc) += "-Xplugin-disable:artima-supersafe",
    scalacOptions in (Test, doc) += "-Xplugin-disable:artima-supersafe",

    scalacOptions in (Compile,doc) ++= Seq(
      "-diagrams",
      "-doc-title", name.value,
      "-doc-root-content", baseDirectory.value + "/rootdoc.txt"),

    autoAPIMappings := true,

    apiURL := Some(url("https://jpl-imce.github.io/gov.nasa.jpl.imce.oml.resolver/latest/api/")),

    libraryDependencies ++= Seq(
      "com.fasterxml.uuid" % "java-uuid-generator" % "3.1.+",
      "com.lihaoyi" % "ammonite" % "1.0.2" cross CrossVersion.full
      ),

    // Avoid unresolvable dependencies from old versions of log4j
    libraryDependencies ~= {
      _ map {
        case m if m.organization == "log4j" =>
          m
            .exclude("javax.jms", "jms")
            .exclude("com.sun.jmx", "jmxri")
            .exclude("com.sun.jdmk", "jmxtools")
        case m => m
      }
    },
    dependencyOverrides += "com.fasterxml.jackson.module" % "jackson-module-paranamer" % Settings.versions.spark_jackson % "compile",
    dependencyOverrides += "com.fasterxml.jackson.module" %% "jackson-module-scala" % Settings.versions.spark_jackson % "compile"
  )
  //.dependsOn(ProjectRef(file("../gov.nasa.jpl.imce.oml.tables"), "tablesJVM"))
  .dependsOnSourceProjectRefOrLibraryArtifacts(
    "tablesJVM",
    "gov.nasa.jpl.imce.oml.tables",
    Some("compile;test->compile"),
    Seq(
      "gov.nasa.jpl.imce" %% "gov-nasa-jpl-imce-oml-tables"
        % Settings.versions.jpl_omf_schema_tables
        % "compile" withSources(),

      "gov.nasa.jpl.imce" %% "gov-nasa-jpl-imce-oml-tables"
        % Settings.versions.jpl_omf_schema_tables artifacts
        Artifact("gov-nasa-jpl-imce-oml-tables", "zip", "zip", "resource")
    )
  )

def dynamicScriptsResourceSettings(projectName: String): Seq[Setting[_]] = {

  import com.typesafe.sbt.packager.universal.UniversalPlugin.autoImport._

  def addIfExists(f: File, name: String): Seq[(File, String)] =
    if (!f.exists) Seq()
    else Seq((f, name))

  val QUALIFIED_NAME = "^[a-zA-Z][\\w_]*(\\.[a-zA-Z][\\w_]*)*$".r

  Seq(
    // the '*-resource.zip' archive will start from: 'dynamicScripts'
    com.typesafe.sbt.packager.Keys.topLevelDirectory in Universal := None,

    // name the '*-resource.zip' in the same way as other artifacts
    com.typesafe.sbt.packager.Keys.packageName in Universal :=
      normalizedName.value + "_" + scalaBinaryVersion.value + "-" + version.value + "-resource",

    // contents of the '*-resource.zip' to be produced by 'universal:packageBin'
    mappings in Universal ++= {
      val dir = baseDirectory.value
      val bin = (packageBin in Compile).value
      val src = (packageSrc in Compile).value
      val doc = (packageDoc in Compile).value
      val binT = (packageBin in Test).value
      val srcT = (packageSrc in Test).value
      val docT = (packageDoc in Test).value

      (dir * ".classpath").pair(rebase(dir, projectName)) ++
        (dir * "*.md").pair(rebase(dir, projectName)) ++
        (dir / "resources" ***).pair(rebase(dir, projectName)) ++
        addIfExists(bin, projectName + "/lib/" + bin.name) ++
        addIfExists(binT, projectName + "/lib/" + binT.name) ++
        addIfExists(src, projectName + "/lib.sources/" + src.name) ++
        addIfExists(srcT, projectName + "/lib.sources/" + srcT.name) ++
        addIfExists(doc, projectName + "/lib.javadoc/" + doc.name) ++
        addIfExists(docT, projectName + "/lib.javadoc/" + docT.name)
    },

    artifacts += {
      val n = (name in Universal).value
      Artifact(n, "zip", "zip", Some("resource"), Seq(), None, Map())
    },
    packagedArtifacts += {
      val p = (packageBin in Universal).value
      val n = (name in Universal).value
      Artifact(n, "zip", "zip", Some("resource"), Seq(), None, Map()) -> p
    }
  )
}