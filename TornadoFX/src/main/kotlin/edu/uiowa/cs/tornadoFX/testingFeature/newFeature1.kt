import tornadofx.*
import javafx.application.Application
import javafx.application.Platform
import javafx.beans.value.ObservableValue
import javafx.concurrent.Worker
import javafx.concurrent.Worker.State
import javafx.geometry.Pos
import javafx.scene.Group
import javafx.scene.Scene
import javafx.scene.web.*
import javafx.stage.Stage
import javax.script.ScriptEngine
import javax.script.ScriptEngineManager
import javax.script.ScriptException
import netscape.javascript.JSObject
import java.io.File
import java.net.URL

class JupyterView: View("JupyterBookOnline"){

    override var root = pane {
        webview {
            engine.load("https://mybinder.org/v2/gh/jupyterlab/jupyterlab-demo/master?urlpath=lab%2Ftree%2Fdemo%2FLorenz.ipynb")
        }
    }
}

//
// class WebLauncher:Application() {
//
//    inner class Bridge {
//
//        fun test() {
//            val manager = ScriptEngineManager()
//            val engine = manager.getEngineByName("javascript")
//
//            try{
//                engine.eval("var x = 10;")
//                engine.eval("var y = 20;")
//                engine.eval("var z = x + y;")
//                engine.eval("print (z);")
//            } catch (ex:ScriptException) {
//                ex.printStackTrace()
//            }
//        }
//
//    }
//
//    private fun createScene():Scene {
//        val webView = WebView()
//        val webEngine = webView.engine
//        webEngine.loadWorker.stateProperty().addListener(
//                { _:ObservableValue<out State>, _:State, newState:State ->
//                    if (newState == Worker.State.SUCCEEDED) {
//            val win = webEngine.executeScript("window") as JSObject
//            win.setMember("Obj", Bridge())
//            val win2 = webEngine.executeScript("window") as JSObject
//            win2.setMember("app", Bridge())
//        }
//                })
//        webEngine.loadContent(
//        "<div onclick='Obj.test();' width='100' height='50'>"
//        + "Click to Test"
//        + "</div><br /><div onClick='app.exit();' width='100' height='50'>"
//        + "Click to Exit"
//        + "</div>"
//        )
//
//        return Scene(Group(webView))
//    }
//
//    override fun start(stage:Stage) {
//        stage.scene = createScene()
//        stage.sizeToScene()
//        stage.show()
//}
//
//companion object {
//    @JvmStatic fun main(args:Array<String>) {
//            Application.launch(*args)
//        }
//    }
//}

