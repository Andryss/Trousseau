```mermaid
erDiagram
    dormitories
    users
    roles
    privileges
    categories
    items
    bookings
    saved_items
    subscriptions
    
    users }o--|| dormitories : ""
    users }o--o{ roles : ""
    roles }o--o{ privileges : ""
    items }o--|| users : ""
    categories }o--o{ items : ""
    bookings }o--|| users : ""
    items ||--o| bookings : ""
    saved_items }o--|| users : ""
    items ||--o{ saved_items : ""
    subscriptions }o--|| users : ""
    categories }|--o{ subscriptions : ""
```