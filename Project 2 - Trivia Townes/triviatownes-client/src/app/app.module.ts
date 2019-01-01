import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { DataTablesModule } from 'angular-datatables';
import { FormsModule } from '@angular/forms';

import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CreatePageComponent } from './create-page/create-page.component';
import { RouterModule, Routes } from '@angular/router';

import { LandingPageComponent } from './landing-page/landing-page.component';
import { LeaderboardPageComponent } from './leaderboard-page/leaderboard-page.component';
import { ServerLobbyComponent } from './server-lobby/server-lobby.component';
import { WaitingPageComponent } from './waiting-page/waiting-page.component';
import { GamePageComponent } from './game-page/game-page.component';
import { SocketTestComponent } from './socket-test/socket-test.component';

@NgModule({
  declarations: [
    AppComponent,
    CreatePageComponent,
    ServerLobbyComponent,
    LandingPageComponent,
    LeaderboardPageComponent,
    WaitingPageComponent,
    GamePageComponent,
    SocketTestComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    RouterModule,
    DataTablesModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
