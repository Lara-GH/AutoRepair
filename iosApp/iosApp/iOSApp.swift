import SwiftUI
import Firebase
import ComposeApp

@main
struct iOSApp: App {

    @UIApplicationDelegateAdaptor(AppDelegate.self) var delegate

    init(){
        KoinKt.doInitKoinIos()
      }
	var body: some Scene {
		WindowGroup {
			ContentView()
		}
	}
}
