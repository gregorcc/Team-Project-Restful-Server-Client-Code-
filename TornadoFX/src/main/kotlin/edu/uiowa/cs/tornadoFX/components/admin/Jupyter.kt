package edu.uiowa.cs.tornadoFX.components.admin

import edu.uiowa.cs.tornadoFX.components.admin.welcomePage.AdminWelcomePageView
import javafx.geometry.Pos
import tornadofx.*

class JupyterView: View("JupyterBookOnline"){

    override var root = borderpane {
        center = webview {

            engine.load("https://mybinder.org/v2/gh/jupyterlab/jupyterlab-demo/master?urlpath=lab%2Ftree%2Fdemo%2FLorenz.ipynb")
        }
        bottom = button("Go Back") {
            action {
                replaceWith(AdminWelcomePageView::class)
            }
        }
    }
}