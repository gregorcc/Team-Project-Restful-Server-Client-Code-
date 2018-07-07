//package edu.uiowa.cs.tornadoFX.testingFeature
//
//import javafx.application.Application
//import javafx.beans.binding.Bindings
//import javafx.beans.property.ReadOnlyObjectWrapper
//import javafx.collections.FXCollections
//import javafx.scene.Group
//import javafx.scene.Scene
//import javafx.scene.control.TableCell
//import javafx.scene.control.TableColumn
//import javafx.scene.control.TableView
//import javafx.stage.Stage
//import tornadofx.*
//
//
//class IndexColumnDemo : Application() {
//    override fun start(stage: Stage) {
//        stage.setTitle("Table height example")
//
//        val table = TableView(FXCollections.observableArrayList<String>(
//                "Stacey", "Kristy", "Mary Anne", "Claudia"
//        ))
//
//        val indexColumn = TableColumn<Bindings,Int>()
//        indexColumn.setCellFactory({ col ->
//            val indexCell = TableCell<Bindings,Int>()
//            val rowProperty = indexCell.tableRowProperty()
//            val rowBinding = Bindings.createObjectBinding({
//                val row = rowProperty?.get()
//                if (row != null) {
//                    val rowIndex = row!!.getIndex()
//                    if (rowIndex < row!!.getTableView().getItems().size()) {
//                        Bindings.createObjectBinding, Integer.toString rowIndex
//                    }
//                }
//            }, rowProperty)
//            indexCell.textProperty().bind(rowBinding)
//            indexCell
//        })
//
//        val nameColumn = TableColumn("name")
//        nameColumn.setCellValueFactory({ f -> ReadOnlyObjectWrapper<T>(f.getValue()) })
//
//        val columns = table.getColumns()
//        columns.add(indexColumn)
//        columns.add(nameColumn)
//
//        val root = Group()
//        root.getChildren().add(table)
//        val scene = Scene(root)
//        stage.setScene(scene)
//        stage.show()
//
//        stage.sizeToScene()
//    }
//
//    companion object {
//
//        @JvmStatic
//        fun main(args: Array<String>) {
//            launch(args)
//        }
//    }
//}