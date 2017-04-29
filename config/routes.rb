Rails.application.routes.draw do
  get 'demo/index'

  root 'users#new'

  get 'signup' => 'users#new'
  resource :users

  get '/login' => 'sessions#new'
  post 'login' => 'sessions#create'
  delete 'logout' => 'sessions#destroy'

  

  # For details on the DSL available within this file, see http://guides.rubyonrails.org/routing.html
end
