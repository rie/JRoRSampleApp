# Filters added to this controller apply to all controllers in the application.
# Likewise, all the methods added will be available for all controllers.

require 'beeu'

class ApplicationController < ActionController::Base
  helper :all # include all helpers, all the time
  include BeeU
end
