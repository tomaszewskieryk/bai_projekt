<template>
  <div>
    <b-navbar class="fixed-top" toggleable="lg" type="light" variant="light">
      <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

      <b-collapse id="nav-collapse" is-nav>
        <b-navbar-nav>
          <b-nav-item href="/">
            <font-awesome-icon icon="home"/> Strona główna
          </b-nav-item>
          <b-nav-item href="/login"><font-awesome-icon icon="sign-in-alt"/> Logowanie</b-nav-item>
          <b-nav-item href="/register"><font-awesome-icon icon="user-plus"/> Rejestracja</b-nav-item>
          <b-nav-item href="/actual"><font-awesome-icon icon="book"/> Stan lodówki</b-nav-item>
        </b-navbar-nav>
      </b-collapse>
    </b-navbar>
  </div>
</template>