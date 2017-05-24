Rails.application.routes.draw do
  get 'demo/index'

  root 'users#new'

  get 'signup' => 'users#new'
  resource :users

  get '/login' => 'sessions#new'
  post 'login' => 'sessions#create'
  delete 'logout' => 'sessions#destroy'

  #api
  namespace :api do
  	namespace :v1 do
  		resources :users, only: [:index, :create, 
  			:show, :update, :destroy]
  		resources :microposts, only: [:index, :create, 
  			:show, :update, :destroy]
  	end
  end

  # For details on the DSL available within this file, see http://guides.rubyonrails.org/routing.html
end
