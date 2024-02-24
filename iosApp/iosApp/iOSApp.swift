import SwiftUI
import Firebase
import ComposeApp

@main
struct iOSApp: App {

    init(){
        FirebaseApp.configure()
        KoinKt.doInitKoinIos()
      }
	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}
