<div id="contentRight">

  <div class="boxRight">
    <h3 class="select"><span>Menu:</span></h3>
    <ul>
      <li><a href="<%= W_PREFIX %>frontpage">Frontpage</a></li>
      <% if(USER == null) {  %>
      <li><a href="<%= W_PREFIX %>login" title="Login">Login</a></li>
      <% } %>
      <% if(USER != null) {  %>
      <li><a href="<%= W_PREFIX %>wikipage_create">New Page</a></li>
      <li><a href="<%= W_PREFIX %>wikipages">Wikipages</a></li>
      <li><a href="<%= W_PREFIX %>files">Files</a></li>
      <li><a href="<%= W_PREFIX %>users">Users</a></li>
      <li>
        <form class="search_form" action="<%= W_PREFIX %>search" method="get">
          <input type="text" id="search_text" name="search_text" value="<%= searchText %>" autocomplete="off"/>
        </form>
      </li>
      <li><%= USER.getUsername() %> <a style="padding:0;" href="<%= W_PREFIX %>wikipages?action=logout">logout</a></li>
      <% } %>
    </ul>
  </div>

  <div id="ajax_search_indicator" style="display:none;position:absolute;left:500px;">Searching ...</div>
  <div id="ajax_search_result_box" style="display:none;border:1px solid black;background-color:white;position:absolute;left:500px;width:300px !important;z-index:2;"></div>
  <script type="text/javascript" language="javascript" charset="utf-8">
  // <![CDATA[
    new Ajax.Autocompleter('search_text','ajax_search_result_box','<%= W_PREFIX %>ajax_search',
        {tokens: ',', indicator: 'ajax_search_indicator', select:'selectme'} );
  // ]]>
  </script>

</div>