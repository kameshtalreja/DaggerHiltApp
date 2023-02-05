package com.cleartech.daggerhilt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.android.scopes.FragmentScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Qualifier
import javax.inject.Singleton

/* generate the components ! */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    /* injecting a field */
    @Inject lateinit var someClass: SomeClass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        println(
            someClass.doSomeOtherThing()
        )

        println(
            someClass.doSomeOtherThing2()
        )

    }
}

@AndroidEntryPoint
class MyFragment : Fragment(){

    @Inject
    lateinit var someClass: SomeClass

}

/** it create when Application.class perform onCreate() methods*/
/** same for ActivityComponent in activity and FragmentComponent in Fragment Class */
@ActivityScoped
class SomeClass
@Inject constructor(
    private val someOtherClass : SomeOtherClass,
){
    fun doAThing() : String {
        return "Look I Did a thing!"
    }

    fun doSomeOtherThing() : String{
        return someOtherClass.doAOtherThing()
    }

    fun doSomeOtherThing2() : String{
        return someOtherClass.doAOtherThing2()
    }


}


class SomeOtherClass
@Inject constructor(
    @Impl1 private val someInterfaceImp: SomeInterface,
    @Impl2 private val someInterfaceImp2: SomeInterface){ 

    fun doAOtherThing() : String {
        return someInterfaceImp.getSomeThing()
    }

    fun doAOtherThing2() : String {
        return someInterfaceImp2.getSomeThing()
    }
}

class SomeInterfaceImp
    @Inject constructor() : SomeInterface {
    override fun getSomeThing(): String {
        return "this text return from interface imp"
    }
}

class SomeInterfaceImp2
    @Inject constructor() : SomeInterface {
    override fun getSomeThing(): String {
        return "this text return from interface imp 2"
    }
}


interface SomeInterface {
    fun getSomeThing() : String
}

@InstallIn(SingletonComponent::class)
@Module
class MyModule {

    @Impl1
    @Singleton
    @Provides
    fun provideSomething(someInterfaceImp: SomeInterfaceImp) : SomeInterface {
        return someInterfaceImp
    }

    @Impl2
    @Singleton
    @Provides
    fun provideSomething2(someInterfaceImp2: SomeInterfaceImp2) : SomeInterface {
        return someInterfaceImp2
    }

}


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Impl1


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class Impl2



